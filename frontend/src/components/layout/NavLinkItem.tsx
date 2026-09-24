import Nav from 'react-bootstrap/Nav'
import { NavLink } from 'react-router-dom'

interface NavLinkItemProps {
	to: string
	text: string
}

const NavLinkItem = (props: NavLinkItemProps) => {
	return (
		<Nav.Link as={NavLink} to={props.to}>
			{props.text}
		</Nav.Link>
	)
}

export default NavLinkItem
