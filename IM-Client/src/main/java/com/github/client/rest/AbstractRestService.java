package com.github.client.rest;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.client.exception.ImException;
import com.github.common.common.Result;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;

public abstract class AbstractRestService<R> {

    protected R restClient;

    public AbstractRestService(Class<R> clazz, String url) {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(JacksonConverterFactory.create(objectMapper))
            .build();

        this.restClient = retrofit.create(clazz);
    }

    protected <T> T doRequest(RestFunction<T> function) {
        try {
            Response<Result<T>> response = function.doRequest();
            if (!response.isSuccessful()) {
                throw new ImException("[rest service] status is not 200, response body: " + response.toString());
            }
            if (response.body() == null) {
                throw new ImException("[rest service] response body is null");
            }
            if (response.body().getCode() != 200) {
                throw new ImException("[rest service] status is not 200, response body: " + new ObjectMapper().writeValueAsString(response.body()));
            }
            return response.body().getData();
        } catch (IOException e) {
            throw new ImException("[rest service] has error", e);
        }
    }

    @FunctionalInterface
    protected interface RestFunction<T> {
        /**
         * 执行一个http请求
         * @return
         * @throws IOException
         */
        Response<Result<T>> doRequest() throws IOException;
    }
}
