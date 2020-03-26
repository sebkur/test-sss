#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <sys/time.h>
#include <fcntl.h>
#include <math.h>
#include <popt.h>
#include <libgen.h>

#include "sss.h"

int main(int argc, char *argv[])
{
	printf("creating shares...\n");

	int n = 3;
	int k = 2;
	sss_Share share[n];
	uint8_t data[32];

	sss_create_shares(share, data, n, k);

	int ns, nb;
	for (ns = 0; ns < n; ns++) {
		printf("share %d:\n", ns);
		sss_Share * s = &share[ns];
		for (nb = 0; nb < sss_SHARE_LEN; nb++) {
			uint8_t b = (*s)[nb];
			printf("%x", b);
		}
		printf("\n");
	}

	return 0;
}
