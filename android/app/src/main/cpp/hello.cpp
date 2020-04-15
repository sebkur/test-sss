#include <stdio.h>
#include <malloc.h>

using namespace std;
extern "C"
{
void foo(char *target) {
    sprintf(target, "hello c-world\n");
}
}