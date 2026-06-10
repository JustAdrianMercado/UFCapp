package com.ucb.app.ranking.data.repository

import com.ucb.app.ranking.domain.model.FighterRanking
import com.ucb.app.ranking.domain.repository.RankingRepository

class RankingRepositoryImpl : RankingRepository {

    override suspend fun getRankings(): List<FighterRanking> {
        return listOf(
            // P4P / Men's
            FighterRanking("1", 1, "Islam Makhachev", "Lightweight", "https://dmxg5wxfqgb4u.cloudfront.net/styles/athlete_bio_full_body/s3/2024-05/MAKHACHEV_ISLAM_L_06-01.png", 27, 1),
            FighterRanking("2", 2, "Jon Jones", "Heavyweight", "https://dmxg5wxfqgb4u.cloudfront.net/styles/athlete_bio_full_body/s3/2023-03/JONES_JON_L_03-04.png", 28, 1),
            FighterRanking("3", 3, "Alex Pereira", "Light Heavyweight", "https://dmxg5wxfqgb4u.cloudfront.net/styles/athlete_bio_full_body/s3/2024-04/PEREIRA_ALEX_L_04-13.png", 11, 2),
            FighterRanking("4", 4, "Ilia Topuria", "Featherweight", "https://dmxg5wxfqgb4u.cloudfront.net/styles/athlete_bio_full_body/s3/2024-02/TOPURIA_ILIA_L_02-17.png", 15, 0),
            
            // Flyweight
            FighterRanking("5", 1, "Alexandre Pantoja", "Flyweight", "", 28, 5),
            FighterRanking("6", 2, "Brandon Moreno", "Flyweight", "", 21, 8),
            
            // Bantamweight
            FighterRanking("7", 1, "Merab Dvalishvili", "Bantamweight", "", 18, 4),
            FighterRanking("8", 2, "Sean O'Malley", "Bantamweight", "", 18, 2),
            
            // Featherweight
            FighterRanking("9", 1, "Ilia Topuria", "Featherweight", "", 15, 0),
            FighterRanking("10", 2, "Alexander Volkanovski", "Featherweight", "", 26, 4),
            
            // Lightweight
            FighterRanking("11", 1, "Islam Makhachev", "Lightweight", "", 27, 1),
            FighterRanking("12", 2, "Arman Tsarukyan", "Lightweight", "", 22, 3),
            
            // Welterweight
            FighterRanking("13", 1, "Belal Muhammad", "Welterweight", "", 24, 3),
            FighterRanking("14", 2, "Leon Edwards", "Welterweight", "", 22, 4),
            
            // Middleweight
            FighterRanking("15", 1, "Dricus Du Plessis", "Middleweight", "", 22, 2),
            FighterRanking("16", 2, "Sean Strickland", "Middleweight", "", 29, 6),
            
            // Light Heavyweight
            FighterRanking("17", 1, "Alex Pereira", "Light Heavyweight", "", 11, 2),
            FighterRanking("18", 2, "Magomed Ankalaev", "Light Heavyweight", "", 19, 1),
            
            // Heavyweight
            FighterRanking("19", 1, "Jon Jones", "Heavyweight", "", 28, 1),
            FighterRanking("20", 2, "Tom Aspinall", "Heavyweight", "", 15, 3),

            // Women's Flyweight
            FighterRanking("21", 1, "Valentina Shevchenko", "Women's Flyweight", "", 24, 4),
            FighterRanking("22", 2, "Alexa Grasso", "Women's Flyweight", "", 16, 4),

            // Women's Strawweight
            FighterRanking("23", 1, "Zhang Weili", "Women's Strawweight", "", 25, 3),
            FighterRanking("24", 2, "Tatiana Suarez", "Women's Strawweight", "", 10, 0)
        )
    }
}
