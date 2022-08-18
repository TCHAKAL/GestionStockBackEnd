package dz.tchakal.gds.repository.interceptor;

import org.hibernate.EmptyInterceptor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.Locale;
@Repository
public class Intercepteur extends EmptyInterceptor {

    @Override
    public String onPrepareStatement(String sql) {
        if(StringUtils.hasLength(sql) && sql.toLowerCase().startsWith("select")){
            if(sql.toLowerCase().contains("where")){
                sql= sql+" and entreprise = 1";
            }else{
                sql= sql+" where entreprise = 1";
            }
        }
        return super.onPrepareStatement(sql);
    }
}