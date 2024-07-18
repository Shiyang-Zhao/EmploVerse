const debounce = <T extends (...args: any[]) => any>(func: T, delay: number): (...funcArgs: Parameters<T>) => void => {
    let timer: NodeJS.Timeout | null = null;

    return function (...args: Parameters<T>): void {
        if (timer !== null) {
            clearTimeout(timer);
        }
        timer = setTimeout(() => {
            func(...args);
        }, delay);
    };
};

export default debounce;