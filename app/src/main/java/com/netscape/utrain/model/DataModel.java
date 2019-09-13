package com.netscape.utrain.model;

public class DataModel {

    /**
     * Message : Created Successfully
     * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6ImE1N2Q0ZDQ0NjA3NDlmY2ZjMTJiNDBjY2E3NTU1ZTAwNjE1Njk0MmVlYjY0YTFlZTRlZTY1YzM5ZDk2ZDlkM2Q0ZjhlOTYyMjkwNjExNGIyIn0.eyJhdWQiOiIxIiwianRpIjoiYTU3ZDRkNDQ2MDc0OWZjZmMxMmI0MGNjYTc1NTVlMDA2MTU2OTQyZWViNjRhMWVlNGVlNjVjMzlkOTZkOWQzZDRmOGU5NjIyOTA2MTE0YjIiLCJpYXQiOjE1NjgwMzQ0NTAsIm5iZiI6MTU2ODAzNDQ1MCwiZXhwIjoxNTk5NjU2ODUwLCJzdWIiOiIxMCIsInNjb3BlcyI6W119.aMMzhjYLGSFQodouLdC_KQrBqq163k1nrkZ2Dc7stQVZ1PNTrcP_WKEZrjPm-QjeOLe0ABWOhXXyUGsgiJ3whB6qCrjqMiPkGnE4vPWJiOLiYPB_3lU8Kn56WXUP1-hH131AzAVxhPqkOLaP14dqJTi6crsTekBz3YhBugXBydQfAn5pt65aGsQ6fDeaDx8OKw1xcBx_aBNHppFalVhEnDtJHuD0HU6ASXWQrNn4dxoZRKUH1_mtgG0aIRu48xaU1LCzf-dv6enHBJLDqCfkwt4-VSJJo0uAHgnjyQ9Cb0MwvDGP2QQT1KCTXSKZhY0Otsi6aLuOMEAjsmuaIS0Rpos8iRkGZV-njwhMnAKnvhZxalTtBIowV1Iq4S9F0L8Cyu1nwEmAgtbf-R4hvzTAwh7mIsGPxzwT4iDflTusE99Jih5vY3Zo936Tq5sR5qHPzbyQoyLMf9em_SRveQFzCGdxXzOLQQd5ylNZS3E8JVNwXTjOl97HOTUFxc6ySYopRdHid-CWy4kWQir1LtlCpZfgKc-6zGzp9DAGOiRSIgOwmKCOOYmzieHAZgEwImJxYKmkyCnXr-5XEudEClkU340aSGHsJOzW5h2nb3Ekjo8tAuShlO9NK9rHnRoT0dceY24HFibmnBjrQu7YJ5_nmKc3WzurfNdUOTCl5Tq728E
     */

    private String Message;
    private String token;
    private AthleteUserModel user;


    public AthleteUserModel getUser() {
        return user;
    }

    public void setUser(AthleteUserModel user) {
        this.user = user;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
