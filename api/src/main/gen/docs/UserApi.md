# UserApi

All URIs are relative to *http://api/v1*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createUserBy**](UserApi.md#createUserBy) | **POST** /users |  |
| [**deleteUserBy**](UserApi.md#deleteUserBy) | **DELETE** /user/{userId} |  |
| [**getUserById**](UserApi.md#getUserById) | **GET** /user/{userId} |  |
| [**getUsersBy**](UserApi.md#getUsersBy) | **GET** /users |  |
| [**logout**](UserApi.md#logout) | **POST** /auth/logout |  |
| [**updateUser**](UserApi.md#updateUser) | **PUT** /user/{userId} |  |


<a name="createUserBy"></a>
# **createUserBy**
> List&lt;User&gt; createUserBy(newUser)



Create new user

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.UserApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://api/v1");

    UserApi apiInstance = new UserApi(defaultClient);
    NewUser newUser = new NewUser(); // NewUser | 
    try {
      List<User> result = apiInstance.createUserBy(newUser);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling UserApi#createUserBy");
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
| **newUser** | [**NewUser**](NewUser.md)|  | [optional] |

### Return type

[**List&lt;User&gt;**](User.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |
| **404** | User already exists |  -  |

<a name="deleteUserBy"></a>
# **deleteUserBy**
> deleteUserBy(userId)



Delete user

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.UserApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://api/v1");

    UserApi apiInstance = new UserApi(defaultClient);
    Integer userId = 56; // Integer | Unique user id
    try {
      apiInstance.deleteUserBy(userId);
    } catch (ApiException e) {
      System.err.println("Exception when calling UserApi#deleteUserBy");
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
| **userId** | **Integer**| Unique user id | |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |
| **404** | User not found |  -  |

<a name="getUserById"></a>
# **getUserById**
> User getUserById(userId)



Get user by id

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.UserApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://api/v1");

    UserApi apiInstance = new UserApi(defaultClient);
    Integer userId = 56; // Integer | Unique user id
    try {
      User result = apiInstance.getUserById(userId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling UserApi#getUserById");
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
| **userId** | **Integer**| Unique user id | |

### Return type

[**User**](User.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |
| **404** | User not found |  -  |

<a name="getUsersBy"></a>
# **getUsersBy**
> List&lt;User&gt; getUsersBy()



Get users

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.UserApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://api/v1");

    UserApi apiInstance = new UserApi(defaultClient);
    try {
      List<User> result = apiInstance.getUsersBy();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling UserApi#getUsersBy");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;User&gt;**](User.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |

<a name="logout"></a>
# **logout**
> logout(body)



Log out

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.UserApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://api/v1");

    UserApi apiInstance = new UserApi(defaultClient);
    Object body = null; // Object | 
    try {
      apiInstance.logout(body);
    } catch (ApiException e) {
      System.err.println("Exception when calling UserApi#logout");
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
| **body** | **Object**|  | [optional] |

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
| **403** | User was not logged in |  -  |

<a name="updateUser"></a>
# **updateUser**
> User updateUser(userId, user)



Update user

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.UserApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://api/v1");

    UserApi apiInstance = new UserApi(defaultClient);
    Integer userId = 56; // Integer | Unique user id
    User user = new User(); // User | 
    try {
      User result = apiInstance.updateUser(userId, user);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling UserApi#updateUser");
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
| **userId** | **Integer**| Unique user id | |
| **user** | [**User**](User.md)|  | [optional] |

### Return type

[**User**](User.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |
| **404** | User not found |  -  |

