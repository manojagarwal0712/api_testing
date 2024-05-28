package api.endpoint;

/**
 * https://petstore.swagger.io/v2/user/createWithArray
 * https://petstore.swagger.io/v2/user/createWithList
 * https://petstore.swagger.io/v2/user
 * https://petstore.swagger.io/v2/user/login?username=abc&password=abc
 * https://petstore.swagger.io/v2/user/logout
 * https://petstore.swagger.io/v2/user
 */

/**
 * All the urls of the APIs that we would hit for api tests.
 */
public class Routes {
    public static String base_url = "https://petstore.swagger.io/v2";

    //user module
    public static String post_url = base_url + "/user";
    public static String get_url = base_url + "/user/{userName}";
    public static String update_url = base_url + "/user/{userName}";
    public static String delete_url = base_url + "/user/{userName}";

    public static String upload_url = base_url + "/pet/{petId}/uploadImage";
    public static String pet_url = base_url + "/pet";
    public static String get_pet_url = base_url + "/pet";
    public static String find_by_status_url = base_url + "/pet/findByStatus";


}
