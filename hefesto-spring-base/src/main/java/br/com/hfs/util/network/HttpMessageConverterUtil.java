package br.com.hfs.util.network;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
//import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
//import org.springframework.oxm.xstream.XStreamMarshaller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class HttpMessageConverterUtil {

    public static List<HttpMessageConverter<?>> getMessageConverters() {
    	List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
    	
        converters.add(createMappingJackson2HttpMessageConverter());
        //converters.add(createXmlHttpMessageConverter());
        converters.add(createByteArrayHttpMessageConverter());
        converters.add(createResourceHttpMessageConverter());
        
        converters.add(new StringHttpMessageConverter());
        converters.add(new AllEncompassingFormHttpMessageConverter());
        
        return converters;
    }

    //@Bean
    private static HttpMessageConverter<Object> createMappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        
        converter.setObjectMapper(objectMapper);
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
        converter.setPrettyPrint(true);
        
        return converter;
    }
    
    //@Bean
    /*
    private static HttpMessageConverter<Object> createXmlHttpMessageConverter() {
        MarshallingHttpMessageConverter xmlConverter = 
          new MarshallingHttpMessageConverter();
 
        XStreamMarshaller xstreamMarshaller = new XStreamMarshaller();
        xmlConverter.setMarshaller(xstreamMarshaller);
        xmlConverter.setUnmarshaller(xstreamMarshaller);
 
        return xmlConverter;
    }
*/
    //@Bean
    private static ByteArrayHttpMessageConverter createByteArrayHttpMessageConverter() {
        ByteArrayHttpMessageConverter arrayHttpMessageConverter = new ByteArrayHttpMessageConverter();
        arrayHttpMessageConverter.setSupportedMediaTypes(MediaTypeUtil.getByteArrayMediaTypes());
        return arrayHttpMessageConverter;
    }

    //@Bean
    private static ResourceHttpMessageConverter createResourceHttpMessageConverter() {
    	ResourceHttpMessageConverter resourceHttpMessageConverter = new ResourceHttpMessageConverter();
    	resourceHttpMessageConverter.setSupportedMediaTypes(MediaTypeUtil.getByteArrayMediaTypes());
        return resourceHttpMessageConverter;
    }
}
