package com.promise.integrationtest.servergroup;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.promise.integrationtest.base.DeleteResourceResponse;
import com.promise.integrationtest.servergroup.dto.GetServerGroupResponse;
import com.promise.integrationtest.servergroup.dto.PostServerGroupRequest;
import com.promise.integrationtest.util.ResourceAssertUtil;
import com.promise.integrationtest.util.RestClient;

public class ServerGroupTest
{
    @BeforeClass
    public static void setUpBeforeClass()
            throws Exception
    {
        System.out.println("setUpBeforeClass");
    }

    @AfterClass
    public static void tearDownAfterClass()
            throws Exception
    {
    }

    @Before
    public void setUp()
            throws Exception
    {
    }

    @After
    public void tearDown()
            throws Exception
    {
    }

    @Test
    public void testHappyPath()
    {
        final String name = "MyServerGroup";
        final String description = "MyServerGroup description.";
        final PostServerGroupRequest request = new PostServerGroupRequest();
        request.setName(name);
        request.setDescription(description);

        // Create a server group.
        final ResponseEntity<GetServerGroupResponse> response1 = RestClient.post(
                "http://192.168.206.130/promise/v1/servergroup/",
                request,
                GetServerGroupResponse.class);
        Assert.assertEquals(HttpStatus.CREATED, response1.getStatusCode());
        ResourceAssertUtil.isResource(response1.getBody());

        // Get it.
        final ResponseEntity<GetServerGroupResponse> response2 = RestClient.get(
                "http://192.168.206.130/promise/v1/servergroup/" + response1.getBody().getId(),
                GetServerGroupResponse.class);
        Assert.assertEquals(HttpStatus.OK, response2.getStatusCode());
        ResourceAssertUtil.isResource(response2.getBody());
        Assert.assertEquals(name, response2.getBody().getName());
        Assert.assertEquals(description, response2.getBody().getDescription());

        // Delete it.
        final ResponseEntity<DeleteResourceResponse> response3 = RestClient.delete(
                "http://192.168.206.130/promise/v1/servergroup/" + response1.getBody().getId(),
                DeleteResourceResponse.class);
        Assert.assertEquals(HttpStatus.ACCEPTED, response3.getStatusCode());
    }
}