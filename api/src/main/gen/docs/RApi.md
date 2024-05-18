# RApi

All URIs are relative to *http://api/v1*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**registration**](RApi.md#registration) | **POST** /auth/registration |  |


<a name="registration"></a>
# **registration**
> registration(registration)



Registration new user

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.RApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://api/v1");

    RApi apiInstance = new RApi(defaultClient);
    Registration registration = new Registration(); // Registration | 
    try {
      apiInstance.registration(registration);
    } catch (ApiException e) {
      System.err.println("Exception when calling RApi#registration");
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
| **registration** | [**Registration**](Registration.md)|  | [optional] |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |
| **404** | User already exists |  -  |

