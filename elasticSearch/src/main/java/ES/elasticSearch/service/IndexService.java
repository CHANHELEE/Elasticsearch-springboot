package ES.elasticSearch.service;

import ES.elasticSearch.document.Vehicle;
import ES.elasticSearch.helper.Indices;
import ES.elasticSearch.helper.Util;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IndexService {
    private static  final ObjectMapper MAPPER = new ObjectMapper();

    private static final Logger LOG = LoggerFactory.getLogger(IndexService.class);
    private final RestHighLevelClient client;

    public Boolean save (final Vehicle vehicle){
        try {
            final String vehicleAsString = MAPPER.writeValueAsString(vehicle);

            final IndexRequest request = new IndexRequest(Indices.VEHICLE_INDEX);
            request.id(vehicle.getId());
            request.source(vehicleAsString, XContentType.JSON);

            final IndexResponse response = client.index(request,RequestOptions.DEFAULT);
            return response!=null && response.status().equals(RestStatus.OK);

        }catch (final Exception e){
            LOG.error(e.getMessage(), e);
            return false;
        }
    }

    public Vehicle getById(final String vehicleId){
        try {
            final GetResponse documentFields = client.get(
                new GetRequest(Indices.VEHICLE_INDEX,vehicleId),
                RequestOptions.DEFAULT
            );


            if( documentFields == null ){
                return null;
            }

            return MAPPER.readValue(documentFields.getSourceAsString(),Vehicle.class);

        }catch (final Exception e){
            LOG.error(e.getMessage(), e);
            return  null;
        }

    }


    private final List<String> INDICES_TO_CREATE = List.of(Indices.VEHICLE_INDEX);
    @PostConstruct
    public void tryToCreateIndices(){
        final String settings = Util.loadAsString("static/es-settings.json");
        for( final  String indexName : INDICES_TO_CREATE ){
            try {
                boolean indexExists = client.indices().exists(new GetIndexRequest(indexName), RequestOptions.DEFAULT);
                if( indexExists){
                    continue;
                }
                final String mappings = Util.loadAsString("static/mappings/"+indexName + ".json");
                if( settings ==null || mappings ==null){
                    LOG.error("Filed to create index with name {}", indexName);
                    continue;
                   }


                final CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName);
                createIndexRequest.settings(settings, XContentType.JSON);
                createIndexRequest.mapping(mappings, XContentType.JSON);

                client.indices().create(createIndexRequest, RequestOptions.DEFAULT);
            }catch (final Exception e){
                LOG.error(e.getMessage(),e);
            }

        }
    }


}
