```cpp
#include <iostream>
#include <string>
#include <regex>
using namespace std;

bool validateEmail(const string& email) {
    regex pattern("[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    return regex_match(email, pattern);
}

bool validateAge(int age) {
    return age >= 1 && age <= 120;
}

int main() {
    string email;
    int age;

    cout << "Enter email: ";
    cin >> email;

    cout << "Enter age: ";
    cin >> age;

    cout << "Email valid: " << (validateEmail(email) ? "YES" : "NO") << endl;
    cout << "Age valid: " << (validateAge(age) ? "YES" : "NO") << endl;

    return 0;
}
```
