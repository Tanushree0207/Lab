#include<iostream>
#include<cstring>
#include<stdexcept>
using namespace std;
class SecureBuffer {
char buf[64];
size_t max_size;
public :
SecureBuffer() : max_size(63)
{
memset(buf,0,sizeof(buf)); }
void safeCopy(const char* src){
if(strlen(src) >= max_size)
throw overflow_error("Buffer overflow prevented : input too large");
strncpy(buf,src,max_size);
buf[max_size]='\0';
}
const char* get() { return buf; }
};
int main(){
SecureBuffer sb;
try{
sb.safeCopy("Hello, Secure World!");
cout << "Stored : " << sb.get() << endl;
string overflow(100,'A');
sb.safeCopy(overflow.c_str());
}catch(const overflow_error& e) {
cout << "Caught : " << e.what() << endl;
}
return 0;
}
