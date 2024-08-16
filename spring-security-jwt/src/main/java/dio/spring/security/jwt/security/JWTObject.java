package dio.spring.security.jwt.security;

import java.util.Date;
import java.util.List;

public class JWTObject {
    private String subject;
    private Date issueAt;   
    private Date expiration;
    private List<String> roles;

    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public Date getIssueAt() {
        return issueAt;
    }
    public void setIssueAt(Date issueAt) {
        this.issueAt = issueAt;
    }
    public Date getExpiration() {
        return expiration;
    }
    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }
    public List<String> getRoles() {
        return roles;
    }
    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    
    
}
