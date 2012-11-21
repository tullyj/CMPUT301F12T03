package ca.ualberta.cs.c301_interfaces;

/**
 * Enum to represent the type of an Item.
 * @author colinhunt
 *
 */
public enum ItemType {
    TEXT {
        @Override
        public String toString() {
            return "Text";
        }
    },
    PHOTO {
        @Override
        public String toString() {
            return "Photo";
        }
    },
    AUDIO {
        @Override
        public String toString() {
            return "Audio";
        }
    },
    VIDEO {
        @Override
        public String toString() {
            return "Video";
        }
    };    
}
