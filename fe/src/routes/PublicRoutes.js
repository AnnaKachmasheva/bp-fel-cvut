import BasicPage from "../pages/BasicPage";
import LoginPage from "../pages/login-page/LoginPage";
import {PageTitles} from "../utils/Constants";
import RegistrationPage from "../pages/registartion-page/RegistrationPage";
import React from "react";
import ResetPasswordPage from "../pages/reset-password/ResetPasswordPage";
import HelloPage from "../pages/hello-page/HelloPage";
import HomePage from "../pages/home-page/HomePage";

export const publicRoutes = [
    {
        path: "/hello",
        main: () => <BasicPage page={<HelloPage/>}
                               title={PageTitles.HELLO}
                               isAuth={false}/>
    },
    {
        path: "/login",
        main: () => <BasicPage page={<LoginPage/>}
                               title={PageTitles.LOGIN}
                               isAuth={false}/>
    },
    {
        path: "/registration",
        main: () => <BasicPage page={<RegistrationPage/>}
                               title={PageTitles.REGISTRATION}
                               isAuth={false}/>
    },
    {
        path: "/reset-password",
        main: () => <BasicPage page={<ResetPasswordPage/>}
                               title={PageTitles.RESET_PASSWORD}
                               isAuth={false}/>
    },
    {
        path: "/home",
        main: () => <BasicPage page={<HomePage/>}
                               title={PageTitles.HOME}
                               isAuth={false}/>
    }

];
