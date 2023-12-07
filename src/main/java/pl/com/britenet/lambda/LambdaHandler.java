package pl.com.britenet.lambda;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.spring.SpringBootLambdaContainerHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class LambdaHandler implements RequestStreamHandler {

    private static Logger logger = LoggerFactory.getLogger(LambdaHandler.class);

    public static final SpringBootLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler;

    static {
        try {
            handler = SpringBootLambdaContainerHandler.getAwsProxyHandler(AmazonLambdaRestApiApplication.class);
        } catch (ContainerInitializationException e) {
            String errMsg = "Could not initialize Spring Boot application";
            logger.error(errMsg);
            throw new RuntimeException(errMsg, e);
        }
    }

    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
    	System.out.println("handler 실행...");
    	
        handler.proxyStream(inputStream, outputStream, context);
        outputStream.close();
    }
}
