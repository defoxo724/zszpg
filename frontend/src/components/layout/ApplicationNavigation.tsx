import Nav from 'react-bootstrap/Nav'
import type { UserType } from '../../types'
import NavLinkItem from './NavLinkItem'

interface ApplicationNavigationProps {
	userType: UserType
}

const ApplicationNavigation = (props: ApplicationNavigationProps) => (
	<Nav className="me-auto">
		<NavLinkItem to="/meals" text="Posiłki" />
		<NavLinkItem to="/categories" text="Kategorie" />
		{props.userType === 'ORDERER' && <NavLinkItem to="/cart" text="Koszyk" />}
	</Nav>
)

export default ApplicationNavigation
