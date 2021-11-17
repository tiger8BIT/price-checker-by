package pricecheckerby.subscription.mail;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode
public final class TokenData {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    @AllArgsConstructor
    private enum FieldNames {
        EMAIL("EMAIL"),
        SUBSCRIPTION_ID("SUBSCRIPTION_ID"),
        CREATE_TIME("CREATE_TIME");
        @Getter
        private String name;
    }
    @Getter private final String email;
    @Getter private final LocalDateTime createTime;
    @Getter private final Long subscriptionId;
    @EqualsAndHashCode.Exclude
    private Map<String, Object> map = new HashMap<>();

    public TokenData(String email, Long subscriptionId, LocalDateTime createTime) {
        this.email = email;
        this.createTime = createTime;
        this.subscriptionId = subscriptionId;
        map.put(FieldNames.EMAIL.name, email);
        map.put(FieldNames.SUBSCRIPTION_ID.name, subscriptionId);
        map.put(FieldNames.CREATE_TIME.name, createTime.format(formatter));
    }

    public TokenData(Map<String, Object> map) {
        this.map = new HashMap<>(map);
        this.email = (String) map.get(FieldNames.EMAIL.name);
        this.subscriptionId = Long.valueOf((Integer) map.get(FieldNames.SUBSCRIPTION_ID.name));
        this.createTime = LocalDateTime.parse((String)map.get(FieldNames.CREATE_TIME.name), formatter);
    }

    public Map<String, Object> getMap() {
        return new HashMap<>(map);
    }
}

