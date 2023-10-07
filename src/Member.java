public class Member {
    private int memberId;
    private String name;

    //constructor
    public Member(int memberId, String name) {
        this.memberId = memberId;
        this.name = name;
    }

    //getters
    public int getMemberId() {
        return memberId;
    }
    
    public String getName() {
        return name;
    }

    //setters
    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public void setName(String name) {
        this.name = name;
    }
}

