static int function() {
    return 10;
}

int main() {
    *((volatile int *) (4)) = function();
    return 0;
}
