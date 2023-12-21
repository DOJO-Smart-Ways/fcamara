
// Generated by CodiumAI

import org.junit.Test;
import static org.junit.Assert.*;

public class ListallTest {


    // Should return a Page object containing all instances of Empresa when no pagination parameters are passed
    @Test
    public void test_no_pagination_parameters() {
        // Mock
        Pageable pageable = Mockito.mock(Pageable.class);
        Page<Empresa> expectedPage = Mockito.mock(Page.class);
        Mockito.when(repositoryEmpresa.findAll(pageable)).thenReturn(expectedPage);

        // Happy path
        Page<Empresa> result = endpointEmpresa.listAll(pageable);
        assertEquals(expectedPage, result);

        // Sad path
        Mockito.verify(repositoryEmpresa).findAll(pageable);
    }

    // Should return a Page object containing the correct number of instances of Empresa when pagination parameters are passed
    @Test
    public void test_pagination_parameters() {
        // Mock
        Pageable pageable = Mockito.mock(Pageable.class);
        Page<Empresa> expectedPage = Mockito.mock(Page.class);
        Mockito.when(repositoryEmpresa.findAll(pageable)).thenReturn(expectedPage);

        // Happy path
        Page<Empresa> result = endpointEmpresa.listAll(pageable);
        assertEquals(expectedPage, result);

        // Sad path
        Mockito.verify(repositoryEmpresa).findAll(pageable);
    }

    // Should return a Page object containing instances of Empresa sorted by the default sort order when no sort parameters are passed
    @Test
    public void test_no_sort_parameters() {
        // Mock
        Pageable pageable = Mockito.mock(Pageable.class);
        Page<Empresa> expectedPage = Mockito.mock(Page.class);
        Mockito.when(repositoryEmpresa.findAll(pageable)).thenReturn(expectedPage);

        // Happy path
        Page<Empresa> result = endpointEmpresa.listAll(pageable);
        assertEquals(expectedPage, result);

        // Sad path
        Mockito.verify(repositoryEmpresa).findAll(pageable);
    }

    // Should return an empty Page object when there are no instances of Empresa
    @Test
    public void test_no_instances() {
        // Mock
        Pageable pageable = Mockito.mock(Pageable.class);
        Page<Empresa> expectedPage = Mockito.mock(Page.class);
        Mockito.when(repositoryEmpresa.findAll(pageable)).thenReturn(expectedPage);

        // Happy path
        Page<Empresa> result = endpointEmpresa.listAll(pageable);
        assertEquals(expectedPage, result);

        // Sad path
        Mockito.verify(repositoryEmpresa).findAll(pageable);
    }

    // Should return an empty Page object when pagination parameters are passed that exceed the number of instances of Empresa
    @Test
    public void test_pagination_parameters_exceed_instances() {
        // Mock
        Pageable pageable = Mockito.mock(Pageable.class);
        Page<Empresa> expectedPage = Mockito.mock(Page.class);
        Mockito.when(repositoryEmpresa.findAll(pageable)).thenReturn(expectedPage);

        // Happy path
        Page<Empresa> result = endpointEmpresa.listAll(pageable);
        assertEquals(expectedPage, result);

        // Sad path
        Mockito.verify(repositoryEmpresa).findAll(pageable);
    }

    // Should return a Page object containing the correct number of instances of Empresa when pagination parameters are passed that are equal to the number of instances of Empresa
    @Test
    public void test_pagination_parameters_equal_instances() {
        // Mock
        Pageable pageable = Mockito.mock(Pageable.class);
        Page<Empresa> expectedPage = Mockito.mock(Page.class);
        Mockito.when(repositoryEmpresa.findAll(pageable)).thenReturn(expectedPage);

        // Happy path
        Page<Empresa> result = endpointEmpresa.listAll(pageable);
        assertEquals(expectedPage, result);

        // Sad path
        Mockito.verify(repositoryEmpresa).findAll(pageable);
    }
}