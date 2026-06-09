# Cleanup Plan — UFC Fight App (Updated)

Last updated after source packages for crypto, movie, github, fakestore, country, collections, detail, chat, payment_card, warehouse, product, product_detail, counter, increment, signin, and nm/login were removed by the user.

---

## 1. 🔴 Critical: DI Modules Reference Deleted Classes (Build/Runtime will break)

The three DI module files still contain `import` statements and registrations for classes whose source files no longer exist. **These MUST be fixed before the app will compile/run.**

### 1.1 `PresentationModule.kt` — 9 dead imports, 9 dead registrations

**Imports to remove:**
```kotlin
import com.ucb.app.config.presentation.viewmodel.ConfigViewModel
import com.ucb.app.counter.presentation.viewmodel.CounterViewModel       // DELETED
import com.ucb.app.country.presentation.viewmodel.CountryViewModel       // DELETED
import com.ucb.app.crypto.presentation.viewmodel.CryptoViewModel         // DELETED
import com.ucb.app.fakestore.presentation.viewmodel.FakeStoreViewModel   // DELETED
import com.ucb.app.github.presentation.viewmodel.GithubViewModel         // DELETED
import com.ucb.app.increment.presentation.viewmodel.IncrementViewModel   // DELETED
import com.ucb.app.movie.presentation.viewmodel.MovieViewModel           // DELETED
import com.ucb.app.portafolio.presentation.viewmodel.PortafolioViewModel
import com.ucb.app.product_detail.presentation.viewmodel.ProductDetailViewModel  // DELETED
import com.ucb.app.signin.presentation.viewmodel.SigninViewModel         // DELETED
```

**Registrations to remove:**
- `viewModelOf(::ProductDetailViewModel)` — deleted
- `viewModelOf(::CounterViewModel)` — deleted
- `viewModelOf(::IncrementViewModel)` — deleted
- `viewModelOf(::GithubViewModel)` — deleted
- `viewModelOf(::SigninViewModel)` — deleted
- `viewModelOf(::MovieViewModel)` — deleted
- `viewModelOf(::CryptoViewModel)` — deleted
- `viewModelOf(::FakeStoreViewModel)` — deleted
- `viewModelOf(::CountryViewModel)` — deleted
- `viewModelOf(::PortafolioViewModel)` — still exists
- `viewModelOf(::ConfigViewModel)` — still exists

**Keep these (UFC-related):**
- `viewModelOf(::LoginViewModel)`
- `viewModelOf(::FightListViewModel)`
- `viewModelOf(::RankingViewModel)`
- `viewModelOf(::LiveViewModel)`
- `viewModelOf(::FightersViewModel)`
- `viewModelOf(::ProfileViewModel)`
- `viewModelOf(::OnboardingViewModel)`

### 1.2 `DomainModule.kt` — 3 dead imports, 3 dead registrations

**Imports to remove:**
```kotlin
import com.ucb.app.crypto.domain.usecase.GetCryptosUseCase       // DELETED
import com.ucb.app.github.domain.usecase.GetAvatarUseCase        // DELETED
import com.ucb.app.movie.domain.usecase.GetMoviesUseCase         // DELETED
```

**Registrations to remove:**
- `singleOf(::GetAvatarUseCase)` — deleted
- `singleOf(::GetMoviesUseCase)` — deleted
- `singleOf(::GetCryptosUseCase)` — deleted

### 1.3 `DataModule.kt` — 12 dead imports, 6 dead registrations

**Imports to remove:**
```kotlin
import com.ucb.app.crypto.data.datasource.CryptoRemoteDataSource     // DELETED
import com.ucb.app.crypto.data.repository.CryptoRepositoryImpl       // DELETED
import com.ucb.app.crypto.data.service.CryptoService                 // DELETED
import com.ucb.app.crypto.domain.repository.CryptoRepository          // DELETED
import com.ucb.app.github.data.datasource.GithubRemoteDataSource      // DELETED
import com.ucb.app.github.data.repository.GithubRepositoryImpl       // DELETED
import com.ucb.app.github.data.service.GitHubApiService              // DELETED
import com.ucb.app.github.domain.repository.GithubRepository          // DELETED
import com.ucb.app.movie.data.datasource.MovieRemoteDatasource       // DELETED
import com.ucb.app.movie.data.repository.MovieRepositoryImpl         // DELETED
import com.ucb.app.movie.data.service.MovieService                   // DELETED
import com.ucb.app.movie.domain.repository.MovieRepository            // DELETED
```

**Registrations to remove:**
```kotlin
singleOf(::GitHubApiService).bind<GithubRemoteDataSource>()
singleOf(::GithubRepositoryImpl).bind<GithubRepository>()
singleOf(::MovieService).bind<MovieRemoteDatasource>()
singleOf(::MovieRepositoryImpl).bind<MovieRepository>()
singleOf(::CryptoService).bind<CryptoRemoteDataSource>()
singleOf(::CryptoRepositoryImpl).bind<CryptoRepository>()
```

### 1.4 `DataModule.kt` — Duplicate FightRepositoryImpl registration

Lines 54-55 register **the same class twice**, which will cause a Koin `DefinitionOverrideException`:

```kotlin
single { FightRepositoryImpl(get()) }.bind<FightRepository>()    // line 54
singleOf(::FightRepositoryImpl).bind<FightRepository>()          // line 55 — DUPLICATE
```

**Fix:** Remove one of them (keep either line).

---

## 2. 🗑️ Still Remaining: Unrelated Infrastructure Features

These are still present in the project. Decide whether to keep or remove each.

### 2.1 `portafolio/` — Firebase Realtime Database demo

**Source files:**
- `composeApp/src/commonMain/kotlin/com/ucb/app/portafolio/` (10 files: AppDatabase, entities, DAOs, FirebaseManager, repository, use cases, UI)
- `composeApp/src/androidMain/kotlin/.../portafolio/data/datasource/FirebaseManager.kt`
- `composeApp/src/iosMain/kotlin/.../portafolio/data/datasource/FirebaseManager.kt` (stub)
- `composeApp/schemas/` (Room DB schema files)

**Wired in:** NavRoute — **ALREADY REMOVED** ✅, but still in PresentationModule, DomainModule, DataModule, AndroidConfigModule, AndroidEventModule

### 2.2 `config/` — Firebase Remote Config

**Source files:**
- `composeApp/src/commonMain/kotlin/com/ucb/app/config/` (9 files)
- `composeApp/src/androidMain/kotlin/.../config/` (2 data source files)
- `composeApp/src/androidMain/kotlin/.../di/AndroidConfigModule.kt`
- `composeApp/src/androidMain/kotlin/.../worker/InitialConfigScheduler.kt`
- `composeApp/src/androidMain/kotlin/.../worker/InitialConfigWorker.kt`

**Wired in:** NavRoute — **ALREADY REMOVED** ✅, but still in DI modules and AndroidApp.kt

### 2.3 `event/` — App event tracking (Room + Firebase)

**Source files:**
- `composeApp/src/commonMain/kotlin/com/ucb/app/event/` (5 files)
- `composeApp/src/androidMain/kotlin/.../event/data/datasource/` (2 files)
- `composeApp/src/androidMain/kotlin/.../di/AndroidEventModule.kt`
- `composeApp/src/androidMain/kotlin/.../lifecycle/AppLifecycleObserver.kt`

**Wired in:** DomainModule (RegisterAppEventUseCase), MainActivity (OPEN/CLOSE events)

### 2.4 Firebase FCM + Remote Config

**Source files:**
- `composeApp/src/androidMain/kotlin/.../notification/FirebaseService.kt`
- `composeApp/src/androidMain/kotlin/.../worker/LogScheduler.kt`
- `composeApp/src/androidMain/kotlin/.../worker/LogUploadWorker.kt`
- `composeApp/google-services.json`

**Wired in:** AndroidApp.kt (RemoteConfig init), MainActivity (FCM token, scheduling), AndroidManifest.xml (FirebaseService declaration)

### 2.5 `onboarding/` — Onboarding screens

**Source files:**
- `composeApp/src/commonMain/kotlin/com/ucb/app/onboarding/` (14 files)
- `composeApp/src/androidMain/kotlin/.../onboarding/data/datasource/` (3 files)
- `composeApp/src/androidMain/kotlin/.../di/AndroidOnboardingModule.kt`

**Wired in:** NavRoute.Onboarding, AppNavHost, PresentationModule, DomainModule, DataModule, AndroidOnboardingModule

**UFC relevance:** This could be kept if you want onboarding screens, or removed if not needed.

---

## 3. 🧹 Empty Directory Skeletons (safe to delete)

After the source packages were removed, empty directory structures remain:

```
composeApp/src/commonMain/kotlin/com/ucb/app/
├── chat/
├── collections/
├── counter/
├── detail/
├── increment/
├── nm/login/
├── payment_card/
├── product/
├── product_detail/
├── signin/
└── warehouse/
```

---

## 4. 🗄️ Resources That Can Be Removed

### 4.1 All drawables — Already removed by user ✅

The `composeResources/drawable/` directory is now empty. No action needed.

### 4.2 Room DB schemas (safe to delete)
```
composeApp/schemas/
├── com.ucb.app.portafolio.data.datasource.AppDatabase/
│   ├── 1.json
│   └── 2.json
```
These are auto-generated and can be regenerated. Delete if you're removing Room/portafolio.

### 4.3 Android native resources (unused drawables)
```
composeApp/src/androidMain/res/
├── drawable/ic_launcher_background.xml
├── drawable-v24/ic_launcher_foreground.xml
├── mipmap-anydpi-v26/ (2 files)
├── mipmap-hdpi/ (2 files)
├── mipmap-mdpi/ (2 files)
├── mipmap-xhdpi/ (2 files)
├── mipmap-xxhdpi/ (2 files)
├── mipmap-xxxhdpi/ (2 files)
```
Keep these if you want the existing launcher icon. Replace if you want a UFC-branded icon.

### 4.4 Localization strings (English + French only)
```
composeApp/src/commonMain/composeResources/values/strings.xml
composeApp/src/commonMain/composeResources/values-en/strings.xml
composeApp/src/commonMain/composeResources/values-fr/strings.xml
```
Most strings reference deleted features (github, portfolio, login). Clean up or replace with UFC content.

### 4.5 iOS app assets
```
iosApp/
├── iosApp/Assets.xcassets/AppIcon.appiconset/app-icon-1024.png
├── iosApp/Preview Content/
├── iosApp/Info.plist
```
Keep if you still target iOS. Replace app icon if desired.

---

## 5. 📦 Gradle Dependencies: Still Relevant After Deletion

### 5.1 Dependencies that can still be removed
| Dependency | Reason |
|---|---|
| `firebase-bom` + `firebase-config` + `firebase-database` + `firebase-messaging` | All Firebase features still present (portafolio, config, event, FCM) |
| `kotlinx-coroutines-play-services` | Only needed for Firebase |
| `androidx-work-runtime-ktx` | Only needed for WorkManager workers |
| `androidx-sqlite-bundled` + `androidx-room-runtime` + `androidx-room-compiler` | Only needed for Room (portafolio) |
| `google-gms-google-services` plugin | Only needed for Firebase |
| `ksp` + `androidx.room` plugins | Only needed for Room |

### 5.2 Dependencies to keep
| Dependency | Reason |
|---|---|
| `compose.runtime`, `foundation`, `material3`, `ui`, `uiToolingPreview` | Core UI framework |
| `compose.components.resources` | Used for string resources |
| `navigation.compose` | Navigation |
| `lifecycle.viewmodelCompose`, `lifecycle.runtimeCompose` | ViewModel support |
| `koin.core`, `koin.compose`, `koin.compose.viewmodel` | DI |
| `koin.android`, `koin.androidx.compose` | Android DI |
| `ktor.client.core`, `ktor.client.content.negotiation`, `ktor.serialization.kotlinx.json` | Fight API |
| `ktor.client.okhttp` | Android Ktor engine |
| `ktor.client.darwin` | iOS Ktor engine |
| `kotlinx.serialization.json` | Serialization for DTOs |
| `coil.compose`, `coil.network` | Image loading |
| `kotlin.test` | Testing |

### 5.3 Unused entries in libs.versions.toml (not referenced anywhere)
- `kotlin-testJunit`
- `junit`
- `androidx-core-ktx`
- `androidx-testExt-junit`
- `androidx-espresso-core`
- `androidx-appcompat`
- `androidx-room-sqlite-wrapper`

### 5.4 Unused plugin
- `androidLibrary` in root `build.gradle.kts` — not applied by any module

---

## 6. ⚠️ Redundancies Still Present

| Issue | Location |
|---|---|
| `firebase-database` declared twice | `composeApp/build.gradle.kts` lines 43 and 116 |
| `compose.uiToolingPreview` in both commonMain and androidMain | `composeApp/build.gradle.kts` lines 36 and 53 |
| `FightRepositoryImpl` registered twice in DataModule | `DataModule.kt` lines 54-55 |

---

## 7. 📄 Empty / Placeholder Files

| File | Problem |
|---|---|
| `fights/data/datasource/FightDao.kt` | Empty class — delete or implement |
| `fights/data/datasource/FightFirebaseDataSource.kt` | Empty file — 0 lines |
| `fights/data/datasource/FightEntity.kt` | **NEW** — check if it existed before |
| `iosMain/.../portafolio/data/datasource/FirebaseManager.kt` | Stub — only prints "not implemented" |
| `composeApp/src/commonMain/kotlin/com/ucb/app/Greeting.kt` | Default template class — not referenced anywhere |

---

## 8. ✅ Recommended Cleanup Order

1. **Fix DI modules first** (PresentationModule, DomainModule, DataModule) — the app won't compile without this
2. **Decide on infrastructure**: Remove or keep portafolio, config, event, Firebase
3. **Clean up MainActivity.kt and AndroidApp.kt** based on infrastructure decision
4. **Delete empty directory skeletons**
5. **Remove unused gradle entries**
6. **Clean up string resources**
7. **Replace app icon** with UFC branding (optional)
