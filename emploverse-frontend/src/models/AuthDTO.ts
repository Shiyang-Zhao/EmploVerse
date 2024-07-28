
interface SignUpDTO {
    username: string;
    email: string;
    password: string;
    confirmPassword: string;
}

interface LoginDTO {
    email: string;
    password: string;
    rememberMe: boolean;
}

interface RequestPasswordResetRequest {
    email: string;
}

interface CompletePasswordResetRequest {
    token: string;
    newPassword: string;
    confirmPassword: string;
}

export type { SignUpDTO, LoginDTO, CompletePasswordResetRequest, RequestPasswordResetRequest }