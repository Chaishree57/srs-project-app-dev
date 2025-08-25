package com.examly.springapp;

import java.io.File;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = SpringappApplication.class)
@AutoConfigureMockMvc
class SpringappApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    // Test adding a spice merchant through the controller
    @Test
    void test_Add_SpiceMerchant() throws Exception {
        String spiceMerchantJson = "{\"name\": \"Spice Trader\",\"spices\": \"Cinnamon, Pepper\",\"experience\": 7,\"storeLocation\": \"Old Bazaar\",\"phoneNumber\": \"9876543210\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/addSpiceMerchant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(spiceMerchantJson)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    // Test adding a spice merchant with invalid phone number (exception case)
    @Test
    void test_Add_SpiceMerchant_With_Invalid_Name() throws Exception {
        // Use an invalid name (contains numbers and special characters)
        String spiceMerchantJson = "{\"name\": \"Exotic Spices123!@#\",\"spices\": \"Saffron, Cloves\",\"experience\": 5,\"storeLocation\": \"Market Square\",\"phoneNumber\": \"9876543210\"}";
    
        mockMvc.perform(MockMvcRequestBuilders.post("/addSpiceMerchant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(spiceMerchantJson)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()) // Expecting a bad request for invalid name
                .andExpect(jsonPath("$").value("Name must not contain special characters or numbers.")); // Adjust the message based on your exception handling
    }
    

    // Test fetching all spice merchants from the controller
    @Test
    void test_Get_AllSpiceMerchants() throws Exception {
        mockMvc.perform(get("/getAllSpiceMerchants")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andReturn();
    }

    // Verify the controller folder exists
    @Test
    public void test_Controller_Directory_Exists() {
        String directoryPath = "src/main/java/com/examly/springapp/controller";
        File directory = new File(directoryPath);
        assertTrue(directory.exists() && directory.isDirectory());
    }

    // Verify the SpiceMerchantController class exists
    @Test
    public void test_SpiceMerchantController_File_Exists() {
        String filePath = "src/main/java/com/examly/springapp/controller/SpiceMerchantController.java";
        File file = new File(filePath);
        assertTrue(file.exists() && file.isFile());
    }

    // Verify the model folder exists
    @Test
    public void test_Model_Directory_Exists() {
        String directoryPath = "src/main/java/com/examly/springapp/model";
        File directory = new File(directoryPath);
        assertTrue(directory.exists() && directory.isDirectory());
    }

    // Verify the SpiceMerchant model file exists
    @Test
    public void test_SpiceMerchant_File_Exists() {
        String filePath = "src/main/java/com/examly/springapp/model/SpiceMerchant.java";
        File file = new File(filePath);
        assertTrue(file.exists() && file.isFile());
    }

    // Verify the repository folder exists
    @Test
    public void test_Repository_Folder_Exists() {
        String directoryPath = "src/main/java/com/examly/springapp/repository";
        File directory = new File(directoryPath);
        assertTrue(directory.exists() && directory.isDirectory());
    }

    // Verify the service folder exists
    @Test
    public void test_Service_Folder_Exists() {
        String directoryPath = "src/main/java/com/examly/springapp/service";
        File directory = new File(directoryPath);
        assertTrue(directory.exists() && directory.isDirectory());
    }

    // Verify the SpiceMerchantService class exists
    @Test
    public void test_SpiceMerchantService_Class_Exists() {
        checkClassExists("com.examly.springapp.service.SpiceMerchantService");
    }

    // Verify the SpiceMerchant model class exists
    @Test
    public void test_SpiceMerchantModel_Class_Exists() {
        checkClassExists("com.examly.springapp.model.SpiceMerchant");
    }

    // Check that the SpiceMerchant model has a 'name' field
    @Test
    public void test_SpiceMerchant_Model_Has_name_Field() {
        checkFieldExists("com.examly.springapp.model.SpiceMerchant", "name");
    }

    // Check that the SpiceMerchant model has a 'spices' field
    @Test
    public void test_SpiceMerchant_Model_Has_spices_Field() {
        checkFieldExists("com.examly.springapp.model.SpiceMerchant", "spices");
    }

    // Check that the SpiceMerchant model has a 'storeLocation' field
    @Test
    public void test_SpiceMerchant_Model_Has_storeLocation_Field() {
        checkFieldExists("com.examly.springapp.model.SpiceMerchant", "storeLocation");
    }

    // Check that the SpiceMerchant model has a 'phoneNumber' field
    @Test
    public void test_SpiceMerchant_Model_Has_phoneNumber_Field() {
        checkFieldExists("com.examly.springapp.model.SpiceMerchant", "phoneNumber");
    }

    // Check that the SpiceMerchantRepo implements JpaRepository
    @Test
    public void test_SpiceMerchantRepo_Extends_JpaRepository() {
        checkClassImplementsInterface("com.examly.springapp.repository.SpiceMerchantRepo",
                "org.springframework.data.jpa.repository.JpaRepository");
    }

    // Verify that CORS configuration class exists
    @Test
    public void test_CorsConfiguration_Class_Exists() {
        checkClassExists("com.examly.springapp.configuration.CorsConfiguration");
    }

    // Verify that CORS configuration has the Configuration annotation
    @Test
    public void test_CorsConfiguration_Has_Configuration_Annotation() {
        checkClassHasAnnotation("com.examly.springapp.configuration.CorsConfiguration",
                "org.springframework.context.annotation.Configuration");
    }

    // Verify that InvalidNameException class exists
    @Test
    public void test_InvalidNameException_Class_Exists() {
        checkClassExists("com.examly.springapp.exception.InvalidNameException");
    }

    // Verify that InvalidNameException extends RuntimeException
    @Test
    public void test_InvalidNameException_Extends_RuntimeException() {
        try {
            Class<?> clazz = Class.forName("com.examly.springapp.exception.InvalidNameException");
            assertTrue(RuntimeException.class.isAssignableFrom(clazz),
                    "InvalidNameException should extend RuntimeException");
        } catch (ClassNotFoundException e) {
            fail("InvalidNameException class does not exist.");
        }
    }

    // Helper method to check if a class exists
    private void checkClassExists(String className) {
        try {
            Class.forName(className);
        } catch (ClassNotFoundException e) {
            fail("Class " + className + " does not exist.");
        }
    }

    // Helper method to check if a field exists in a class
    private void checkFieldExists(String className, String fieldName) {
        try {
            Class<?> clazz = Class.forName(className);
            clazz.getDeclaredField(fieldName);
        } catch (ClassNotFoundException | NoSuchFieldException e) {
            fail("Field " + fieldName + " in class " + className + " does not exist.");
        }
    }

    // Helper method to check if a class implements an interface
    private void checkClassImplementsInterface(String className, String interfaceName) {
        try {
            Class<?> clazz = Class.forName(className);
            Class<?> interfaceClazz = Class.forName(interfaceName);
            assertTrue(interfaceClazz.isAssignableFrom(clazz));
        } catch (ClassNotFoundException e) {
            fail("Class " + className + " or interface " + interfaceName + " does not exist.");
        }
    }

    // Helper method to check if a class has a specific annotation
    private void checkClassHasAnnotation(String className, String annotationName) {
        try {
            Class<?> clazz = Class.forName(className);
            Class<?> annotationClazz = Class.forName(annotationName);
            assertTrue(clazz.isAnnotationPresent((Class<? extends java.lang.annotation.Annotation>) annotationClazz));
        } catch (ClassNotFoundException e) {
            fail("Class " + className + " or annotation " + annotationName + " does not exist.");
        }
    }
}
