package com.muses.common.external.eaxy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ElementFilters {

    private static class ChildQuery implements ElementQuery {

        private final ElementQuery parent;
        private final ElementQuery child;

        private ChildQuery(ElementQuery parent, ElementQuery child) {
            this.parent = parent;
            this.child = child;
        }

        @Override
        public ElementSet search(ElementSet elements) {
            return child.search(parent.search(elements));
        }

    }

    private static final class ElementDescendantQuery implements ElementQuery {
        private final ElementFilter filter;
        private ElementQuery next;

        private ElementDescendantQuery(ElementQuery filter) {
            if (filter instanceof ChildQuery) {
                this.filter = (ElementFilter) ((ChildQuery) filter).parent;
                this.next = ((ChildQuery) filter).child;
            } else {
                // TODO: This look wrong!
                this.filter = any();
                this.next = identity();
            }
        }

        @Override
        public ElementSet search(ElementSet elements) {
            ArrayList<Element> result = new ArrayList<Element>();
            for (Element element : elements) {
                findDescendants(element, result);
            }
            return elements.nestedSet(this, result);
        }

        private void findDescendants(Element element, ArrayList<Element> result) {
            for (Element child : element.elements()) {
                if (filter.matches(child)) {
                    result.addAll(next.search(new ElementSet(child)).elements());
                }
                findDescendants(child, result);
            }
        }

        @Override
        public String toString() {
            return "...//" + filter;
        }
    }

    private static final class ElementPositionFilter implements ElementQuery {
        private final Number position;

        private ElementPositionFilter(Number position) {
            this.position = position;
        }

        @Override
        public ElementSet search(ElementSet elements) {
            if (intValue() < elements.size()) {
                return elements.nestedSet(this, Arrays.asList(elements.get(intValue())));
            } else {
                return elements.nestedSet(this, new ArrayList<Element>());
            }
        }

        private int intValue() {
            return position.intValue();
        }

        @Override
        public String toString() {
            return position.toString();
        }
    }

    private final static Pattern ATTRIBUTE_PATTERN = Pattern.compile("(.*)\\[(.+)=(.+)\\]");
    private final static Pattern ID_PATTERN = Pattern.compile("(.*)#(.+)");
    private final static Pattern CLASS_NAME_PATTERN = Pattern.compile("(.*)\\.(.+)");

    public static ElementQuery stringFilter(String filter) {
        if (filter.isEmpty() || filter.equals("*")) {
            return any();
        }
        ElementFilter elementFilter;
        elementFilter = attrFilter(filter);
        if (elementFilter != null)
            return elementFilter;
        elementFilter = idFilter(filter);
        if (elementFilter != null)
            return elementFilter;
        elementFilter = classNameFilter(filter);
        if (elementFilter != null)
            return elementFilter;
        return tagName(filter);
    }

    public static ElementQuery create(Object[] path) {
        ElementQuery query = identity();
        for (int i = path.length - 1; i >= 0; i--) {
            Object filter = path[i];
            query = "...".equals(filter) ? descendant(query) : child(query, filter);
        }
        return query;
    }

    private static ChildQuery child(ElementQuery query, Object filter) {
        return new ChildQuery(filter(filter), query);
    }

    public static ElementDescendantQuery descendant(ElementQuery query) {
        return new ElementDescendantQuery(query);
    }

    private static ElementQuery identity() {
        return new ElementQuery() {
            @Override
            public ElementSet search(ElementSet elements) {
                return elements;
            }
        };
    }

    public static ElementQuery filter(Object filter) {
        if (filter instanceof Attribute) {
            return attrFilter((Attribute) filter);
        } else if (filter instanceof CharSequence) {
            return stringFilter(filter.toString());
        } else if (filter instanceof QualifiedName) {
            return tagName((QualifiedName) filter);
        } else if (filter instanceof Number) {
            return position((Number) filter);
        } else {
            return (ElementQuery) filter;
        }
    }

    public static ElementQuery position(Number filter) {
        return new ElementPositionFilter(filter);
    }

    public static ElementFilter idFilter(String filter) {
        Matcher matcher = ID_PATTERN.matcher(filter);
        if (matcher.matches()) {
            return and(filter, tagName(matcher.group(1)), attrFilter("id", matcher.group(2)));
        }
        return null;
    }

    public static ElementFilter classNameFilter(String filter) {
        Matcher matcher = CLASS_NAME_PATTERN.matcher(filter);
        if (matcher.matches()) {
            return and(filter, tagName(matcher.group(1)), attrFilter("class", matcher.group(2)));
        }
        return null;
    }

    public static ElementFilter attrFilter(String filter) {
        Matcher matcher = ATTRIBUTE_PATTERN.matcher(filter);
        if (matcher.matches()) {
            return and(filter, tagName(matcher.group(1)), attrFilter(matcher.group(2), matcher.group(3)));
        }
        return null;
    }

    public static ElementFilter attrFilter(String attributeName, String value) {
        return attrFilter(Namespace.NO_NAMESPACE.attr(attributeName, value));
    }

    public static ElementFilter attrFilter(final Attribute attr) {
        return new ElementFilter(attr.toString()) {
            @Override
            public boolean matches(Element element) {
                return attr.getValue().equals(element.attr(attr.getKey()));
            }
        };
    }

    public static ElementFilter and(String name, final ElementFilter... filters) {
        return new ElementFilter(name) {
            @Override
            public boolean matches(Element element) {
                for (ElementFilter filter : filters) {
                    if (!filter.matches(element))
                        return false;
                }
                return true;
            }
        };
    }

    public static ElementFilter tagName(final String tagName) {
        if (tagName.isEmpty() || tagName.equals("*"))
            return any();
        return new ElementFilter(tagName) {
            @Override
            public boolean matches(Element element) {
                return element.getName().matches(tagName);
            }
        };
    }

    public static ElementFilter tagName(final QualifiedName tagName) {
        return new ElementFilter(tagName.toString()) {
            @Override
            public boolean matches(Element element) {
                return tagName.matches(element.getName());
            }
        };
    }

    public static ElementFilter any() {
        return new ElementFilter("*") {
            @Override
            public boolean matches(Element element) {
                return true;
            }
        };
    }

}
