package code;

class Return extends RuntimeException {
    Return(Object value) {
        super(null, null, false, false);
        this.value = value;
    }

    final Object value;
}
