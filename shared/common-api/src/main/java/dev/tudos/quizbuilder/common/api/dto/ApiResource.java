package dev.tudos.quizbuilder.common.api.dto;

/**
 * Interface for API resources.
 * <p/>
 *     Implementations of this interface should be enums, where each enum value represents a specific resource.
 *     The enum value should be the resource name in lowercase.
 *     The enum value should also implement the {@link ApiResource} interface.
 *     The enum value should have a constructor with a single parameter of type {@link String}, which should be the resource name.
 */
public interface ApiResource {
    /**
     * Get the name of resource.
     *
     * @return the name of resource. Example: user, file, etc.
     */
    String getName();
}
