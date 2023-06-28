package it.unimib.finalproject.server.repositories;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import com.fasterxml.jackson.databind.json.JsonMapper;

import it.unimib.finalproject.server.model.Projection;
import it.unimib.finalproject.server.utils.dbclient.DbConnector;
import it.unimib.finalproject.server.utils.dbclient.resp.types.RESPError;
import jakarta.inject.Singleton;
import jakarta.inject.Inject;

@Singleton
public class ProjectionRepository {
    @Inject
    DbConnector db;

    @Inject
    JsonMapper mapper;

    public ArrayList<Projection> getProjectionsByMovie(int movieId) {
        return null;
    }

    public Projection getProjectionById(int proj_id) throws NumberFormatException, IOException, RESPError {
        Optional<String> resp = db.hgetString("projections", ""+proj_id);

        if(!resp.isPresent() || resp.get().isEmpty())  
            return null;
        
        Projection projection = mapper.readValue(resp.get(), Projection.class);
        return projection;
    }
    
}
