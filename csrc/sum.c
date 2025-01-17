static int count(int n) {
    int cnt = 0;
    for(int i = 0;i <= n; i++) cnt += i;
    return cnt;
}

int main() {
    *((volatile int *) (4)) = count(10);
    return 0;
}
