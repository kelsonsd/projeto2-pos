package br.edu.ifpb.pos.fb;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 *
 * @author Kelson
 */
@ApplicationPath("rest")
public class ApplicationConf extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();         
        resources.add(br.edu.ifpb.pos.fb.ProverServico.class);
        return resources;
    }
}
