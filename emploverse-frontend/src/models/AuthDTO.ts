
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

export type { SignUpDTO, LoginDTO }