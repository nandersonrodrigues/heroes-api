package com.digitalinnovation.one.heroesapi.config;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.digitalinnovation.one.heroesapi.constants.HeroesConstants;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;


public class HeroesData {
    public static void main (String[] args) throws Exception {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder
                .standard()
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(
                                HeroesConstants.ENDPOINT_DYNAMO,
                                HeroesConstants.REGION_DYNAMO
                        ))
                .build();
        DynamoDB dynamoDB = new DynamoDB(client);
        Table table = dynamoDB.getTable("Heroes_Table");
        Item hero = new Item()
                .withPrimaryKey("id", 1)
                .withString("name", "Batman")
                .withString("universe", "DC")
                .withNumber("films", 4);

        PutItemOutcome outcome = table.putItem(hero);
    }
}
