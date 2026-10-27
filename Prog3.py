#include <stdio.h>
#include <string.h>
int checkPassword(char password[])
{
char storedPassword[12];
strcpy(storedPassword, "goodpass");
if (strcmp(password, storedPassword) == 0)
return 1;
return 0;
}
int authenticate()
{
char password[12];
printf("Enter Password: ");
gets(password);
return checkPassword(password);
}
int main()
{
int status;
status = authenticate();
if (status)
printf("Access granted\n");
else
printf("Access denied\n");
return 0;
}
