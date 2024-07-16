import { FormDTO } from "@/models/FormDTO";
import { Dispatch, SetStateAction, ChangeEvent } from "react";

const handleChange = <T extends FormDTO>(
    e: ChangeEvent<HTMLInputElement>,
    setFormData: Dispatch<SetStateAction<T>>
) => {
    const { name, value, type, checked } = e.target;

    setFormData((prevState) => ({
        ...prevState,
        [name]: type === "checkbox" ? checked : value,
    }));
};

export default handleChange;
