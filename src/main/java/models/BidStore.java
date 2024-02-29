package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BidStore {

    private static List<Bid> bids = new ArrayList<>();

    public void addBid(Bid bid) {
        bids.add(bid);
    }

    public static Map<String, List<User>> collectBidsBySymbol(UserStore userStore) {
        Map<String, List<User>> organizationUsersMap = new HashMap<>();
        List<User> users = new ArrayList<>(userStore.getUsers().values());

        for (User user : users) {
            organizationUsersMap.computeIfAbsent(user.getOrganization(), k -> new ArrayList<>()).add(user);
        }

        return organizationUsersMap;
    }
}
