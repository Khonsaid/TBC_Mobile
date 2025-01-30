package uz.gita.latizx.usecase.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import uz.gita.latizx.usecase.auth.SignInResendUseCase
import uz.gita.latizx.usecase.auth.SignInVerifyUseCase
import uz.gita.latizx.usecase.auth.SignUpResendUseCase
import uz.gita.latizx.usecase.auth.SignUpVerifyUseCase
import uz.gita.latizx.usecase.auth.impl.SignInResendUCImpl
import uz.gita.latizx.usecase.auth.impl.SignInVerifyUseCaseImpl
import uz.gita.latizx.usecase.auth.impl.SignUpResendUseCaseImpl
import uz.gita.latizx.usecase.auth.impl.SignUpVerifyUseCaseImpl
import uz.gita.latizx.usecase.card.AddCardUseCase
import uz.gita.latizx.usecase.card.GetCardsUseCase
import uz.gita.latizx.usecase.card.impl.AddCardUseCaseImpl
import uz.gita.latizx.usecase.card.impl.GetCardsUseCaseImpl
import uz.gita.latizx.usecase.home.TotalBalanceUseCase
import uz.gita.latizx.usecase.home.impl.TotalBalanceUseCaseImpl
import uz.gita.latizx.usecase.transfer.GetCardOwnerByPanUseCase
import uz.gita.latizx.usecase.transfer.GetFreeUseCase
import uz.gita.latizx.usecase.transfer.GetHistoryUseCase
import uz.gita.latizx.usecase.transfer.TransferResendUseCase
import uz.gita.latizx.usecase.transfer.TransferUseCase
import uz.gita.latizx.usecase.transfer.TransferVerifyUseCase
import uz.gita.latizx.usecase.transfer.impl.GetCardOwnerByPanUseCaseImpl
import uz.gita.latizx.usecase.transfer.impl.GetFreeUseCaseImpl
import uz.gita.latizx.usecase.transfer.impl.GetHistoryUseCaseImpl
import uz.gita.latizx.usecase.transfer.impl.TransferResendUseCaseImpl
import uz.gita.latizx.usecase.transfer.impl.TransferUseCaseImpl
import uz.gita.latizx.usecase.transfer.impl.TransferVerifyUseCaseImpl
import uz.gita.latizx.usecase.auth.PinCodeUseCase
import uz.gita.latizx.usecase.auth.SignInUseCase
import uz.gita.latizx.usecase.auth.SignUpUseCase
import uz.gita.latizx.usecase.auth.impl.PinCodeUseCaseImpl
import uz.gita.latizx.usecase.auth.impl.SignInUseCaseImpl
import uz.gita.latizx.usecase.auth.impl.SignUpUseCaseImpl

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {

    /* Auth UseCase*/

    @[Binds ViewModelScoped]
    fun bindSignInUC(impl: SignInUseCaseImpl): SignInUseCase

    @[Binds ViewModelScoped]
    fun bindSignUpUC(impl: SignUpUseCaseImpl): SignUpUseCase

    @[Binds ViewModelScoped]
    fun bindSignUpVerifyUC(impl: SignUpVerifyUseCaseImpl): SignUpVerifyUseCase

    @[Binds ViewModelScoped]
    fun bindSignInVerifyUC(impl: SignInVerifyUseCaseImpl): SignInVerifyUseCase

    @[Binds ViewModelScoped]
    fun bindSignInResendUC(impl: SignInResendUCImpl): SignInResendUseCase

    @[Binds ViewModelScoped]
    fun bindSignUpResendUC(impl: SignUpResendUseCaseImpl): SignUpResendUseCase

    /* Home UseCase */
    @[Binds ViewModelScoped]
    fun bindTotalBalanceUC(impl: TotalBalanceUseCaseImpl): TotalBalanceUseCase

    /* Card UseCase */
    @[Binds ViewModelScoped]
    fun bindGetCards(impl: GetCardsUseCaseImpl): GetCardsUseCase

    @[Binds ViewModelScoped]
    fun bindAddCard(impl: AddCardUseCaseImpl): AddCardUseCase

    /* Transfer UseCase */
    @[Binds ViewModelScoped]
    fun bindGetCardOwnerByPanCard(impl: GetCardOwnerByPanUseCaseImpl): GetCardOwnerByPanUseCase

    @[Binds ViewModelScoped]
    fun bindGetFreeUseCase(impl: GetFreeUseCaseImpl): GetFreeUseCase

    @[Binds ViewModelScoped]
    fun bindTransferUseCase(impl: TransferUseCaseImpl): TransferUseCase

    @[Binds ViewModelScoped]
    fun bindTransferVerifyUseCase(impl: TransferVerifyUseCaseImpl): TransferVerifyUseCase

    @[Binds ViewModelScoped]
    fun bindTransferResendUseCase(impl: TransferResendUseCaseImpl): TransferResendUseCase

    @[Binds ViewModelScoped]
    fun bindGetHistoryUseCase(impl: GetHistoryUseCaseImpl): GetHistoryUseCase

    @[Binds ViewModelScoped]
    fun bindPinCodeUseCase(impl: PinCodeUseCaseImpl): PinCodeUseCase
}