package uz.gita.latizx.tbcmobile.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import uz.gita.latizx.hwl64_module.screen.auth.intro.IntroDirections
import uz.gita.latizx.presenter.auth.intro.IntroContract
import uz.gita.latizx.presenter.auth.pin_code.PinCodeContract
import uz.gita.latizx.presenter.auth.sign_in.SignInContract
import uz.gita.latizx.presenter.auth.sign_up.SignUpContract
import uz.gita.latizx.presenter.auth.verify.VerifyContract
import uz.gita.latizx.presenter.card.add.AddCardContract
import uz.gita.latizx.presenter.card.cards.CardsContract
import uz.gita.latizx.presenter.card.info.CardsInfoContract
import uz.gita.latizx.presenter.history.transaction.TransactionContract
import uz.gita.latizx.presenter.history.transaction_detail.TransactionDetailContract
import uz.gita.latizx.presenter.home.HomeContract
import uz.gita.latizx.presenter.splash.SplashContract
import uz.gita.latizx.presenter.success.SuccessContract
import uz.gita.latizx.presenter.transfer.fee.FeeContract
import uz.gita.latizx.presenter.transfer.recipient.RecipientContract
import uz.gita.latizx.presenter.transfer.transfer.TransferContract
import uz.gita.latizx.tbcmobile.screen.auth.pin_code.PinCodeDirections
import uz.gita.latizx.tbcmobile.screen.auth.sign_in.SignInDirections
import uz.gita.latizx.tbcmobile.screen.auth.sign_up.SignUpDirections
import uz.gita.latizx.tbcmobile.screen.auth.verify.VerifyDirections
import uz.gita.latizx.tbcmobile.screen.card.add.AddCardDirections
import uz.gita.latizx.tbcmobile.screen.card.cards.CardsDirections
import uz.gita.latizx.tbcmobile.screen.card.info.CardsInfoDirections
import uz.gita.latizx.tbcmobile.screen.history.transaction.TransactionsDirections
import uz.gita.latizx.tbcmobile.screen.history.transaction_detail.TransactionDetailDirectionsImpl
import uz.gita.latizx.tbcmobile.screen.main.home.HomeDirections
import uz.gita.latizx.tbcmobile.screen.splash.SplashDirections
import uz.gita.latizx.tbcmobile.screen.succes.SuccessDirections
import uz.gita.latizx.tbcmobile.screen.transfers.fee.FeeDirections
import uz.gita.latizx.tbcmobile.screen.transfers.recipient.RecipientDirections
import uz.gita.latizx.tbcmobile.screen.transfers.transfer.TransferDirections

@Module
@InstallIn(ViewModelComponent::class)
interface DirectionModule {
    @Binds
    fun bindIntroDirections(impl: IntroDirections): IntroContract.Directions

    @Binds
    fun bindSignInDirections(impl: SignInDirections): SignInContract.Directions

    @Binds
    fun bindSignUpDirections(impl: SignUpDirections): SignUpContract.Directions

    @Binds
    fun bindVerifyDirections(impl: VerifyDirections): VerifyContract.Directions

    @Binds
    fun bindHomeDirections(impl: HomeDirections): HomeContract.Directions

//    @Binds
//    fun bindPaymentDirections(impl: PaymentDirections): PaymentContract.Directions
//
//    @Binds
//    fun bindTransfersDirections(impl: TransfersDirections): MoneyTransfersContract.Directions

    @Binds
    fun bindTransactionsDirections(impl: TransactionsDirections): TransactionContract.Directions

    @Binds
    fun bindCardsDirections(impl: CardsDirections): CardsContract.Directions

    @Binds
    fun bindCardsInfoDirections(impl: CardsInfoDirections): CardsInfoContract.Directions

    @Binds
    fun bindAddCardDirections(impl: AddCardDirections): AddCardContract.Directions

    @Binds
    fun bindPinCodeDirections(impl: PinCodeDirections): PinCodeContract.Directions

    @Binds
    fun bindSplashDirections(impl: SplashDirections): SplashContract.Directions

    @Binds
    fun bindRecipientDirections(impl: RecipientDirections): RecipientContract.Directions

    @Binds
    fun bindTransferDirections(impl: TransferDirections): TransferContract.Directions

    @Binds
    fun bindFeeDirections(impl: FeeDirections): FeeContract.Directions

    @Binds
    fun bindSuccessDirections(impl: SuccessDirections): SuccessContract.Directions

    @Binds
    fun bindTransactionDetailDirections(impl: TransactionDetailDirectionsImpl): TransactionDetailContract.Directions
}