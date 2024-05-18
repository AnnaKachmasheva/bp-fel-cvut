# ErApi

All URIs are relative to *http://api/v1*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**login**](ErApi.md#login) | **POST** /auth/login |  |


<a name="login"></a>
# **login**
> LoginToken login(logIn)



Log in

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ErApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://api/v1");

    ErApi apiInstance = new ErApi(defaultClient);
    LogIn logIn = new LogIn(); // LogIn | 
    try {
      LoginToken result = apiInstance.login(logIn);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ErApi#login");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **logIn** | [**LogIn**](LogIn.md)|  | [optional] |

### Return type

[**LoginToken**](LoginToken.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |
| **401** | Invalid email or password |  -  |
| **404** | User not found by email |  -  |

