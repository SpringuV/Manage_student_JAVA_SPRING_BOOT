//package vutran.my_first_project_spring_boot.management_student.Security;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
//@Component
//public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
//    @Override
//    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
//        // Redirect user to the URL they were trying to access before login
//        String targetUrl = determineTargetUrl(request, response); // Determine the URL that the user tries to access before logging in. The method will return the URL that the user requested before
//        if (response.isCommitted()) { // Kiểm tra xem phản hồi HTTP đã được gửi hay chưa. Phương thức isCommitted trả về true nếu phản hồi đã được gửi đến client và không thể thay đổi nữa.
//            return; //  Nếu phản hồi đã được gửi, phương thức handle sẽ kết thúc và không thực hiện bất kỳ hành động nào khác. Điều này ngăn không cho xảy ra lỗi do cố gắng sửa đổi phản hồi đã được cam kết.
//        }
//        getRedirectStrategy().sendRedirect(request, response, targetUrl); // Nếu phản hồi chưa được cam kết, phương thức này sử dụng RedirectStrategy để chuyển hướng người dùng đến targetUrl. getRedirectStrategy()
//        // là một phương thức từ SimpleUrlAuthenticationSuccessHandler trả về chiến lược chuyển hướng hiện tại, mặc định là DefaultRedirectStrategy. Phương thức sendRedirect của RedirectStrategy sẽ thực hiện việc chuyển hướng tới URL đích.
//    }
//}
