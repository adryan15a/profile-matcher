package com.profilematcher.records;

import java.util.Set;

public record MatchingCriteriaDetails(Set<String> gameCountries, Set<String> gameItems, Set<String> userItems) {
}
