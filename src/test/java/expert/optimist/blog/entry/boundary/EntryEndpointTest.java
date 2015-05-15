package expert.optimist.blog.entry.boundary;


import expert.optimist.blog.entry.control.EntryService;
import expert.optimist.blog.entry.entity.Entry;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.anyObject;
import static org.mockito.Mockito.when;

public class EntryEndpointTest {

    @Mock
    EntryService mockService;

    @InjectMocks
    private EntryEndpoint endpoint = new EntryEndpoint();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void should_call_service_update() {
        Random r = new Random();
        Long id = (long) r.nextInt(50);
        Entry entry = new Entry();
        entry.setId(id);

        Entry updatedExpected = new Entry();
        updatedExpected.setId(id * 2);

        when(mockService.update(anyObject())).thenReturn(updatedExpected);

        Entry updated = endpoint.update(id, entry);
        assertEquals(updatedExpected, updated);
    }

    @Test
    public void should_set_the_id_of_the_entry() {
        Random r = new Random();
        Long id = (long) r.nextInt(100);
        Entry entry = new Entry();

        Entry updatedExpected = new Entry();
        updatedExpected.setId(id * 2);

        when(mockService.update(anyObject())).thenReturn(updatedExpected);

        Entry updated = endpoint.update(id, entry);
        assertEquals(updatedExpected, updated);
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throw_IllegalArgumentException() {
        Long id = 2L;
        Entry entry = new Entry();
        entry.setId(id);

        endpoint.update(5L, entry);
    }

}
