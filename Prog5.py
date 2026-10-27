#include <iostream>
#include <memory>
using namespace std;
class SecureData {
unique_ptr<int[]> data;
size_t size;
public:
SecureData(size_t n) : size(n), data(make_unique<int[]>(n)) {

cout << "Allocated " << n << " integers" << endl;
}
~SecureData() { cout << "Memory freed automatically (RAII)" << endl; }
void set(size_t idx, int val) {
if (idx >= size)
throw out_of_range("Index out of bounds");
data[idx] = val;
}
int get(size_t idx) const {
if (idx >= size)
throw out_of_range("Index out of bounds");
return data[idx];
}
};
int main() {
{
SecureData sd(5);
for (int i = 0; i < 5; i++) sd.set(i, i * 10);
cout << "Values: ";
for (int i = 0; i < 5; i++) cout << sd.get(i) << " ";
cout << endl;
try { sd.get(10); }
catch(const out_of_range& e) { cout << "Caught: " << e.what() << endl; }
}
cout << "Scope ended, destructor called automatically" << endl;
return 0;

}
