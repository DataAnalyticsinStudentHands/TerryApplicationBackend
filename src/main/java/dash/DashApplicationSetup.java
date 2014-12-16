package dash;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.message.filtering.EntityFilteringFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

import dash.errorhandling.AppExceptionMapper;
import dash.errorhandling.GenericExceptionMapper;
import dash.errorhandling.NotFoundExceptionMapper;
import dash.filters.LoggingResponseFilter;
import dash.pojo.ActivityResource;
import dash.pojo.AwardResource;
import dash.pojo.ApplicationResource;
import dash.pojo.ChildResource;
import dash.pojo.CourseworkResource;
import dash.pojo.EmploymentResource;
import dash.pojo.InstitutionResource;
import dash.pojo.MilitaryResource;
import dash.pojo.ScholarshipResource;
import dash.pojo.TransferActivityResource;
import dash.pojo.TransferApplicationResource;
import dash.pojo.UniversityResource;
import dash.pojo.UsersResource;
import dash.pojo.VolunteerResource;

/**
 * Registers the components to be used by the JAX-RS application
 *
 * @author plindner
 *
 */
public class DashApplicationSetup extends ResourceConfig {

	/**
	 * Register JAX-RS application components.
	 */
	public DashApplicationSetup() {
		// register application resources
		register(ActivityResource.class);
		register(ApplicationResource.class);
		register(AwardResource.class);
		register(ChildResource.class);
		register(CourseworkResource.class);
		register(EmploymentResource.class);
		register(InstitutionResource.class);
		register(MilitaryResource.class);
		register(ScholarshipResource.class);
		register(TransferActivityResource.class);
		register(TransferApplicationResource.class);
		register(UniversityResource.class);
		register(UsersResource.class);
		register(VolunteerResource.class);

		// register filters
		register(RequestContextFilter.class);
		register(LoggingResponseFilter.class);

		// register exception mappers
		register(GenericExceptionMapper.class);
		register(AppExceptionMapper.class);
		register(NotFoundExceptionMapper.class);

		// register features
		register(JacksonFeature.class);
		register(MultiPartFeature.class);
		register(EntityFilteringFeature.class);
	}
}