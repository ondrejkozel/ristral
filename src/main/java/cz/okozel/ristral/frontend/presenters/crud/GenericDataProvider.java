package cz.okozel.ristral.frontend.presenters.crud;

import com.vaadin.flow.component.crud.CrudFilter;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.SortDirection;
import cz.okozel.ristral.backend.entity.AbstractSchemaEntity;
import cz.okozel.ristral.backend.entity.schema.Schema;
import cz.okozel.ristral.backend.repository.generic.GenericRepository;
import cz.okozel.ristral.backend.service.entity.generic.GenericSchemaService;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class GenericDataProvider<T extends AbstractSchemaEntity, S extends GenericSchemaService<T, ? extends GenericRepository<T>>> extends AbstractBackEndDataProvider<T, CrudFilter> {

    protected S service;
    private final Class<T> tridaObjektu;
    protected Schema schema;

    private Consumer<Long> sizeChangeListener;

    public GenericDataProvider(S service, Class<T> tridaObjektu, Schema schema) {
        this.service = service;
        this.tridaObjektu = tridaObjektu;
        this.schema = schema;
    }

    @Override
    protected Stream<T> fetchFromBackEnd(Query<T, CrudFilter> query) {
        int offset = query.getOffset();
        int limit = query.getLimit();
        Stream<T> stream = service.findAll(schema).stream();
        if (query.getFilter().isPresent()) {
            stream = stream
                    .filter(predicate(query.getFilter().get()))
                    .sorted(comparator(query.getFilter().get()));
        }
        return stream.skip(offset).limit(limit);
    }

    // TODO: 17.11.2021 někdy v budoucnu nechat filtrování a řazení na databázi
    private Predicate<T> predicate(CrudFilter filter) {
        return filter.getConstraints().entrySet().stream()
                .map(constraint -> (Predicate<T>) objekt -> {
                    try {
                        Object value = valueOf(constraint.getKey(), objekt);
                        return value != null && value.toString().toLowerCase()
                                .contains(constraint.getValue().toLowerCase());
                    } catch (Exception e) {
                        e.printStackTrace();
                        return false;
                    }
                })
                .reduce(Predicate::and)
                .orElse(e -> true);
    }

    private Comparator<T> comparator(CrudFilter filter) {
        return filter.getSortOrders().entrySet().stream()
                .map(sortClause -> {
                    try {
                        @SuppressWarnings({"unchecked", "rawtypes"})
                        Comparator<T> comparator = Comparator.comparing(objekt -> (Comparable) valueOf(sortClause.getKey(), objekt));
                        if (sortClause.getValue() == SortDirection.DESCENDING) {
                            comparator = comparator.reversed();
                        }
                        return comparator;
                    } catch (Exception ex) {
                        return (Comparator<T>) (o1, o2) -> 0;
                    }
                })
                .reduce(Comparator::thenComparing)
                .orElse((o1, o2) -> 0);
    }

    private Object valueOf(String fieldName, T objekt) {
        try {
            Field field = tridaObjektu.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(objekt);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    protected int sizeInBackEnd(Query<T, CrudFilter> query) {
        long count = fetchFromBackEnd(query).count();
        if (sizeChangeListener != null) sizeChangeListener.accept(count);
        return (int) count;
    }

    void setSizeChangeListener(Consumer<Long> listener) {
        sizeChangeListener = listener;
    }

    public void uloz(T objekt) {
        objekt.setSchema(schema);
        service.save(objekt);
    }

    public void smaz(T objekt) {
        service.delete(objekt);
    }

}
