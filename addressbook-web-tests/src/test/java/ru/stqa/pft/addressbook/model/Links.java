package ru.stqa.pft.addressbook.model;

import com.google.common.collect.ForwardingSet;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Links extends ForwardingSet<LinkData> {

        private Set<LinkData> delegate;

        public Links (Links links) {
            this.delegate = new HashSet<LinkData>(links.delegate);
        }

        public Links () {
            this.delegate = new HashSet<LinkData>();
        }

        public Links (Collection<LinkData> links) {
            this.delegate = new HashSet<LinkData>(links);
        }

        @Override
        protected Set<LinkData> delegate() {
            return delegate;
        }

        public Links withAdded(LinkData link){
            Links links = new Links(this);
            links.add(link);
            return links;
        }

        public Links without(GroupData group){
            Links links = new Links(this);
            links.remove(group);
            return links;
        }

}


