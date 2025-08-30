package cheap.electricity.services;

public class Api {
  private final String apiUrl;

  public Api(String apiUrl){
    this.apiUrl = apiUrl;
  }

  public void showPrices(){
    System.out.println(apiUrl);
  }
}


