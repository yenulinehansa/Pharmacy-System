package Email;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class EmailConfig {
    private String host;
    private int port;
    private String username;
    private String password;
    private boolean auth;
    private boolean starttls;
}
