package com.playspace.repository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.playspace.model.Score;

public class ScoreRepository {

	private static ScoreRepository scoreRepository;

	private Set<Score> scores;

	private ScoreRepository() {
		// init mock data
		scores = new HashSet<>();

		Score s1 = new Score();
		s1.setLevel(1);
		s1.setUserId(4711);
		s1.setPoints(Arrays.asList(500, 231, 25667, 2134, 23, 234));
		scores.add(s1);

		Score s2 = new Score();
		s2.setLevel(2);
		s2.setUserId(4711);
		s2.setPoints(Arrays.asList(234, 234, 3, 57456, 34));
		scores.add(s2);

		Score s3 = new Score();
		s3.setLevel(2);
		s3.setUserId(131);
		s3.setPoints(Arrays.asList(234, 234, 3, 57456, 34));
		scores.add(s3);
	}

	public static ScoreRepository getInstance() {
		if (scoreRepository == null) {
			scoreRepository = new ScoreRepository();
		}
		return scoreRepository;
	}

	public synchronized String findHighestScoresByLevelId(int levelId) {
		Set<Score> levelScores = scores.stream().filter(score -> score.getLevel() == levelId).collect(Collectors.toSet());
		String userScore = "";
		
		for (Score score : levelScores) {
			int max = 0;
			for (int points : score.getPoints()) {
				if (max < points) {
					max = points;
				}
			}
			
			userScore += score.getUserId() + "=" + max + ",";
		}
		return userScore.replaceAll(",$", "");
	}

}
