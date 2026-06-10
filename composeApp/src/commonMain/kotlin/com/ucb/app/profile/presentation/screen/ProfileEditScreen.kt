package com.ucb.app.profile.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.ucb.app.profile.presentation.state.ProfileEditEffect
import com.ucb.app.profile.presentation.state.ProfileEditEvent
import com.ucb.app.profile.presentation.viewmodel.ProfileEditViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProfileEditScreen(
    onNavigateBack: () -> Unit,
    onChangePassword: () -> Unit,
    viewModel: ProfileEditViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                ProfileEditEffect.NavigateBack -> onNavigateBack()
                ProfileEditEffect.NavigateToChangePassword -> onChangePassword()
                is ProfileEditEffect.ShowMessage -> snackbarHostState.showSnackbar(effect.message)
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(horizontal = 18.dp, vertical = 24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(22.dp))
                    .background(
                        Brush.verticalGradient(
                            listOf(Color(0xFF280000), Color(0xFFA00000), Color(0xFFE10600))
                        )
                    )
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 28.dp, vertical = 22.dp)
            ) {
                IconButton(
                    onClick = { viewModel.onEvent(ProfileEditEvent.OnBackClick) },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    AsyncImage(
                        model = state.avatarUrl,
                        contentDescription = "Profile picture",
                        modifier = Modifier
                            .size(84.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )

                    Surface(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .size(26.dp),
                        shape = CircleShape,
                        color = Color(0xFFE10600)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit picture",
                            tint = Color.White,
                            modifier = Modifier.padding(6.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = "Edit Profile",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        color = Color.White
                    )
                } else {
                    ProfileEditTextField(
                        label = "First Name",
                        value = state.firstName,
                        onValueChange = { viewModel.onEvent(ProfileEditEvent.OnFirstNameChanged(it)) }
                    )
                    ProfileEditTextField(
                        label = "Last Name",
                        value = state.lastName,
                        onValueChange = { viewModel.onEvent(ProfileEditEvent.OnLastNameChanged(it)) }
                    )
                    ProfileEditTextField(
                        label = "Username",
                        value = state.username,
                        onValueChange = { viewModel.onEvent(ProfileEditEvent.OnUsernameChanged(it)) }
                    )
                    ProfileEditTextField(
                        label = "Email",
                        value = state.email,
                        onValueChange = { viewModel.onEvent(ProfileEditEvent.OnEmailChanged(it)) }
                    )
                    ProfileEditTextField(
                        label = "Phone Number",
                        value = state.phoneNumber,
                        onValueChange = { viewModel.onEvent(ProfileEditEvent.OnPhoneNumberChanged(it)) }
                    )
                    ProfileEditDropdown(
                        label = "Birthday",
                        value = state.birthday,
                        options = listOf("Birth", "January 12", "May 30", "December 16"),
                        onSelected = { viewModel.onEvent(ProfileEditEvent.OnBirthdayChanged(it)) }
                    )
                    ProfileEditDropdown(
                        label = "Gender",
                        value = state.gender,
                        options = listOf("Gender", "Female", "Male", "Prefer not to say"),
                        onSelected = { viewModel.onEvent(ProfileEditEvent.OnGenderChanged(it)) }
                    )

                    state.error?.let { error ->
                        Text(
                            text = error,
                            color = Color.White,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    Button(
                        onClick = { viewModel.onEvent(ProfileEditEvent.OnSaveClick) },
                        enabled = state.canSave && !state.isSaving,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF720000),
                            disabledContainerColor = Color(0xFF720000).copy(alpha = 0.45f)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = if (state.isSaving) "Saving..." else "Save Changes",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = { viewModel.onEvent(ProfileEditEvent.OnChangePasswordClick) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF720000)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Change Password",
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ProfileEditTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    Text(
        text = label,
        color = Color.White,
        fontSize = 10.sp,
        modifier = Modifier.padding(bottom = 3.dp)
    )
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .height(47.dp),
        singleLine = true,
        shape = RoundedCornerShape(8.dp),
        colors = profileEditTextFieldColors(),
        textStyle = androidx.compose.ui.text.TextStyle(fontSize = 12.sp, color = Color.Black)
    )
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
private fun ProfileEditDropdown(
    label: String,
    value: String,
    options: List<String>,
    onSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Text(
        text = label,
        color = Color.White,
        fontSize = 10.sp,
        modifier = Modifier.padding(bottom = 3.dp)
    )
    Box {
        OutlinedTextField(
            value = value,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(47.dp)
                .clickable { expanded = true },
            enabled = false,
            singleLine = true,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = Color.Black
                )
            },
            shape = RoundedCornerShape(8.dp),
            colors = profileEditTextFieldColors(disabled = true),
            textStyle = androidx.compose.ui.text.TextStyle(fontSize = 12.sp, color = Color.Black)
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
private fun profileEditTextFieldColors(disabled: Boolean = false) = OutlinedTextFieldDefaults.colors(
    focusedContainerColor = Color.White,
    unfocusedContainerColor = Color.White,
    disabledContainerColor = Color.White,
    focusedBorderColor = Color.Transparent,
    unfocusedBorderColor = Color.Transparent,
    disabledBorderColor = Color.Transparent,
    focusedTextColor = Color.Black,
    unfocusedTextColor = Color.Black,
    disabledTextColor = Color.Black,
    cursorColor = if (disabled) Color.Transparent else Color(0xFFE10600)
)
