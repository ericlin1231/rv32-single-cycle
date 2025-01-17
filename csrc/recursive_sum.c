static int recursive_sum(int n)
{
    if(n == 1 || n == 0) return n;
    else return n + recursive_sum(n - 1);
}

int main()
{
    *((volatile int *) (4)) = recursive_sum(3);
    return 0;
}
