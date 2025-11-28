public class PersonalityClassifier {
    public static String classify(int score) throws InvalidPersonalityScoreException {
        if (score < 50 || score > 100)
            throw new InvalidPersonalityScoreException("Personality score must be between 50 and 100");

        if (score >= 90)
            return "Leader";
        if (score >= 70)
            return "Balanced";
        return "Thinker";
    }
}

