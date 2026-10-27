#include <iostream>
#include <fstream>
#include <string>
#include <stdexcept>
using namespace std;

bool isSafePath(const string& path) {
return path.find("..") == string::npos &&
path.find("/etc") == string::npos;
}
void secureWrite(const string& filename, const string& data) {
if (!isSafePath(filename))
throw runtime_error("Unsafe file path detected");
ofstream f(filename);
if (!f) throw runtime_error("Cannot open file");
f << data;
cout << "Written to " << filename << endl;
}


string secureRead(const string& filename)
 {

if (!isSafePath(filename))

throw runtime_error("Unsafe file path detected");

ifstream f(filename);

if (!f) throw runtime_error("File not found");

return string((istreambuf_iterator<char>(f)),

istreambuf_iterator<char>());
}


int main()

 {
try 
{
secureWrite("/tmp/secure_test.txt", "Safe content here");
string data = secureRead("/tmp/secure_test.txt");
cout << "Read: " << data << endl;
secureWrite("../../etc/passwd", "malicious data");
} 
catch(const runtime_error& e) 
{
cout << "Caught: " << e.what() << endl;
}
return 0;
}

